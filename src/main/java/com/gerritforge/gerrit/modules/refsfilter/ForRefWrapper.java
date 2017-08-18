// Copyright (C) 2017 GerritForge Ltd.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.gerritforge.gerrit.modules.refsfilter;

import com.google.gerrit.extensions.restapi.AuthException;
import com.google.gerrit.reviewdb.client.Change;
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.notedb.ChangeNotes;
import com.google.gerrit.server.permissions.PermissionBackend.ForChange;
import com.google.gerrit.server.permissions.PermissionBackend.ForRef;
import com.google.gerrit.server.permissions.PermissionBackendException;
import com.google.gerrit.server.permissions.RefPermission;
import com.google.gerrit.server.query.change.ChangeData;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForRefWrapper extends ForRef {
  private static final Logger log = LoggerFactory.getLogger(ForRefWrapper.class);
  private final ForRef defaultForRef;
  private CurrentUser user;

  public ForRefWrapper(CurrentUser user, ForRef defaultForRef) {
    this.user = user;
    this.defaultForRef = defaultForRef;
  }

  @Override
  public ForRef user(CurrentUser user) {
    return defaultForRef.user(user);
  }

  @Override
  public ForChange change(ChangeData cd) {
    return forChange(() -> cd.change()).orElse(defaultForRef.change(cd));
  }

  @Override
  public ForChange change(ChangeNotes notes) {
    return forChange(() -> notes.getChange()).orElse(defaultForRef.change(notes));
  }

  @Override
  public ForChange indexedChange(ChangeData cd, ChangeNotes notes) {
    return change(cd);
  }

  @FunctionalInterface
  public interface ChangeMaterializer {
    Change get() throws Exception;
  }

  private Optional<ForChange> forChange(ChangeMaterializer change) {
    try {
      if (change.get().getStatus().isClosed()) {
        return Optional.of(new HideForChange(user));
      }
    } catch (Exception e) {
      log.error("Unable to read change data: falling back to default behavior", e);
    }
    return Optional.empty();
  }

  @Override
  public void check(RefPermission perm) throws AuthException, PermissionBackendException {
    defaultForRef.check(perm);
  }

  @Override
  public Set<RefPermission> test(Collection<RefPermission> permSet)
      throws PermissionBackendException {
    return defaultForRef.test(permSet);
  }
}

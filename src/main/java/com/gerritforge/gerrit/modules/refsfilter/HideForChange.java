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
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.permissions.ChangePermissionOrLabel;
import com.google.gerrit.server.permissions.PermissionBackend.ForChange;
import com.google.gerrit.server.permissions.PermissionBackendException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class HideForChange extends ForChange {
  private final CurrentUser user;

  public HideForChange(CurrentUser user) {
    this.user = user;
  }

  @Override
  public CurrentUser user() {
    return user;
  }

  @Override
  public ForChange user(CurrentUser user) {
    return new HideForChange(user);
  }

  @Override
  public void check(ChangePermissionOrLabel perm) throws AuthException, PermissionBackendException {
    accessDenied();
  }

  @Override
  public <T extends ChangePermissionOrLabel> Set<T> test(Collection<T> permSet)
      throws PermissionBackendException {
    return Collections.emptySet();
  }

  private void accessDenied() throws PermissionBackendException {
    throw new PermissionBackendException("Change is not visible");
  }
}

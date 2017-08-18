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

import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.permissions.PermissionBackend;
import com.google.gerrit.server.project.DefaultPermissionBackend;
import com.google.inject.Inject;

public class RefsFilterPermissionBackend extends PermissionBackend {
  private final DefaultPermissionBackend defaultBackend;
  private final WithUserWrapper.Factory filteredRefsUserFactory;

  @Inject
  RefsFilterPermissionBackend(
      DefaultPermissionBackend defaultBackend, WithUserWrapper.Factory filteredRefsUserFactory) {
    this.defaultBackend = defaultBackend;
    this.filteredRefsUserFactory = filteredRefsUserFactory;
  }

  @Override
  public WithUser user(CurrentUser user) {
    return filteredRefsUserFactory.get(user, defaultBackend.user(user));
  }
}

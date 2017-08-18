# Gerrit Ref filter

Gerrit lib module to allow filtering out refs in the Git advertizing
protocol phase.

## How to build

Build this module as it was a Gerrit plugin:

- Clone Gerrit source tree
- Clone the refsfilter source tree
- Link the ```refsfilter``` directory to Gerrit ```/plugins/refsfilter```
- From Gerrit source tree run ```bazel build plugins/refsfilter```
- The ```refsfilter.jar``` module is generated under ```/bazel-genfiles/plugins/refsfilter/```

## How install

Copy ```refsfilter.jar``` library to Gerrit ```/lib``` and add the following
two extra settings to ```gerrit.config```:

```
[gerrit]
  installModule = com.gerritforge.gerrit.modules.refsfilter.GuiceModule
```

## How to configure filtering

The refsfilter module defines a new global capability called "Filter out closed changes refs".
By default the capability isn't assigned to any user or group, thus the module installation
has no side effects.

To enable a group of users of getting a "filtered list" of refs (e.g. CI jobs):
- Define a new group of users (e.g. Builders)
- Add a user to that group (e.g. Add 'jenkins' to the Builders group)
- Go to the All-Projects ACLs, add the "Filter out closed changes refs" and assign to the group (e.g. Builders)

*NOTE* Gerrit makes a super-simplified ACL evaluation if all the projects are globally readable (e.g. project has
a READ rule to refs/*). To enable the closed changes filtering you need to disable any global read rule
for the group that needs refs filtering.


load("//tools/bzl:junit.bzl", "junit_tests")
load(
    "//tools/bzl:plugin.bzl",
    "gerrit_plugin",
    "PLUGIN_DEPS",
    "PLUGIN_TEST_DEPS",
)

gerrit_plugin(
    name = "refsfilter",
    srcs = glob(["src/main/java/**/*.java"]),
    resources = glob(["src/main/resources/**/*"]),
)

#gerrit_plugin(
#    name = "refsfilter",
#    srcs = glob(["src/main/java/**/*.java"]),
#    resources = glob(["src/main/resources/**/*"]),
#)


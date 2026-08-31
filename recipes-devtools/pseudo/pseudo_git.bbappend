# Use our replacement for files referenced by the original pseudo recipe.
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Update pseudo to a version containing proper openat2 support.
PV = "1.9.4"
SRCREV = "6c0d8c6b81ca7c2ef2b5a9a996605e1a51814442"

# These patches were required by the old pseudo revision but are already
# incorporated upstream in newer pseudo versions.
SRC_URI:remove = " \
    file://0001-configure-Prune-PIE-flags.patch \
    file://glibc238.patch \
"


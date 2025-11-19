SUMMARY = "A very basic Wayland image with a terminal"

IMAGE_FEATURES += "splash package-management ssh-server-dropbear hwcodecs weston"

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_BASE_INSTALL += "gtk+3-demo"
CORE_IMAGE_BASE_INSTALL += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'weston-xwayland matchbox-terminal', '', d)}"

QB_MEM = "-m 512"

require include/rz-distro-common.inc
require include/rz-modules-common.inc

IMAGE_INSTALL:append = "\
  packagegroup-ea-dx-extended \
  ea-resizefs \
  auditd \
  libgpiod \
  libgpiod-tools \
  gstreamer1.0-plugins-bad \
  gstreamer1.0-plugins-good \
  python3-pip \
  python3-virtualenv \
  htop \
  iperf3 \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://0001-Changed-to-L4X.R2W32X16D8S32.ADEE.patch \
	file://0001-Created-a-configuration-for-L4.R1W16X16D8S21.ADEE-0-.patch \
"


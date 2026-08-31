FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:rzg3e-ea-som = "file://Flash_Writer_SCIF_RZG3E_CUSTOM_LPDDR4X.mot"
SRC_URI:rzg3l-ea-som = "file://Flash_Writer_SCIF_RZG3L_USER_LPDDR4.mot"


do_deploy:append:rzg3l-family() {
	install -d ${DEPLOYDIR}
	install -m 755 ${S}/Flash_Writer_SCIF_RZG3L_USER_LPDDR4.mot ${DEPLOYDIR}
}

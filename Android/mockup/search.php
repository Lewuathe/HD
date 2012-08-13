<?php
header("Content-Type: application/xml; charset=UTF-8");

if ($_FILES['upload-file']['name']) {
?>
    <pictures>
        <picture uri="http://<?php echo "192.168.1.12"//$_SERVER["REMOTE_ADDR"]; ?>/~mazda/picts/12829581.png" />
        <picture uri="http://<?php echo "192.168.1.12"//$_SERVER["REMOTE_ADDR"]; ?>/~mazda/picts/172179757_3678ac9317_o.png" />
        <picture uri="http://<?php echo "192.168.1.12"//$_SERVER["REMOTE_ADDR"]; ?>/~mazda/picts/2384420649_4a777c69e4_o.jpeg" />
        <picture uri="http://<?php echo "192.168.1.12"//$_SERVER["REMOTE_ADDR"]; ?>/~mazda/picts/351431480_fd915e8b88.jpeg" />
        <picture uri="http://<?php echo "192.168.1.12"//$_SERVER["REMOTE_ADDR"]; ?>/~mazda/picts/3953409521_71fa8ae989_b.jpeg" />
        <picture uri="http://<?php echo "192.168.1.12"//$_SERVER["REMOTE_ADDR"]; ?>/~mazda/picts/71439760_0f64129f0c.jpeg" />
        <picture uri="http://<?php echo "192.168.1.12"//$_SERVER["REMOTE_ADDR"]; ?>/~mazda/picts/logo.gif" />
        <picture uri="http://<?php echo "192.168.1.12"//$_SERVER["REMOTE_ADDR"]; ?>/~mazda/picts/yahoo_logo.jpeg" />
    </pictures>
<?php
}
?>
<!-- Fileアップロードが出来ているときのみXMLを返却します -->



<!DOCTYPE html>
<html>

<head>
    <title>
        Sessão de Usuário
    </title>
    <meta charset="utf-8"/>
    <!-- CSS's imports -->
    <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
    <link href="../css/userPage.css" rel="stylesheet" />
    <link href="../css/navBar.css" rel="stylesheet" type="text/css" />
    <link href="../css/datePicker.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="../vendor/sweetalert/css/sweetalert.css">
</head>
<body onload="userFirstSession()">
    <!-- Include of the navBar -->
    <?php include 'userPageSideBar.php';?>
    <div class="container-fluid">
        <form class="register-form" id="voucherForm" action="http://127.0.0.1" method="POST">
            <div class="row row-centered">
                <div class="col-lg-12 col-centered">
                    <picture>
                        <img class="img-circle" height="180" id="userAvatar" width="180">
                    </picture>
                </div>
            </div>
            <div class="row row-centered">
                <div class="col-lg-12 col-centered">
                    <div class="userName" id="userName">
                        Usuário
                    </div>
                </div>
            </div>
            <div class="row row-centered">
                <div class="col-lg-12 col-centered">
                    <div class="userBlood" id="userBlood">
                        Tipo Sanguíneo
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <label for="date">
                        DATA
                    </label>
                        <input id='dtVoucher' data-format="dd-MM-yyyy" type='text' class="form-control" placeholder="dd-MM-yyyy" />
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <label for="code">
                        CÓDIGO
                    </label>
                    <div class="form-group">
                        <input type="Código" class="form-control" id="nmVoucher" placeholder="XXXX-XXXXXXX">
                    </div>
                </div>
            </div>
            <hr />
            <div class="row row-centered">
                <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 col-centered">
                    <button class="btn btn-default logbutton" type="submit">
                        Validar!
                    </button>
                </div>
            </div>
        </form>
    </div>
    
        <!-- JavaScript's Files -->
    <script src="../vendor/jquery/jquery.min.js"></script>
    <script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="../vendor/sweetalert/js/sweetalert.min.js"></script>
    <script src="../vendor/moment/moment.js"></script>
    <script src="../vendor/date-picker/datePicker.js"></script>
    <script src="../js/userUpdate.js"></script>
    <script src="../js/userVoucherValidation.js"></script>
    <script src="../js/userPageSideBar.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#dtVoucher').datetimepicker({
                locale: 'pt-BR',
                format: 'DD/MM/YYYY'
            });
            $('#dtVoucher').datetimepicker();
        });
        $('#dtVoucher').data('date');
    </script>
</body>
</html>
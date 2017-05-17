<!DOCTYPE html>
<html>
    <head>
        <title>
            Sessão de Usuário
        </title>
        <meta charset="utf-8"/>
        <!-- System's CSS -->
        <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="../css/userPage.css" rel="stylesheet" type="text/css"/>
        <link href="../css/navBar.css" rel="stylesheet" type="text/css"/>
        <link href="../vendor/sweetalert/css/sweetalert.css" rel="stylesheet" type="text/css"/>
    </head>
    <body onload="userSession()">
        <!-- Include of the navBar -->
        <?php include 'userPageSideBar.php';?>
        <div class="container-fluid">
            <form class="register-form" action="http://127.0.0.1" method="POST" id="updateData">
                <div class="row row-centered">
                    <div class="col-lg-12 col-centered">
                        <picture>
                            <img class="img-circle" height="180" id="userAvatar" width="180">
                            </img>
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
                        <label for="cpf">
                            CPF
                        </label>
                        <input class="form-control" id="cpf" name="cpf" type="text" readonly>
                        </input>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <label for="email">
                            EMAIL
                        </label>
                        <input class="form-control" id="email" name="email" onclick="clearField(this.id)" onfocusout="notBlank(this.id)" type="text">
                        </input>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <label for="cidade">
                            CIDADE
                        </label>
                        <select class="form-control" id="city" title="Cidade">
                            <option>Belo Horizonte</option>
                            <option>Contagem</option>
                            <option>Betim</option>
                        </select>
                        </input>
                    </div>
                </div>
                <hr>
                    <div class="row row-centered">
                        <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 col-centered">
                            <button class="btn btn-default logbutton">
                                Alterar
                            </button>
                        </div>
                    </div>
                </hr>
            </form>
        </div>
        <!-- System's JavaScript -->
        <script src="../vendor/jquery/jquery.min.js">
        </script>
        <script src="../vendor/bootstrap/js/bootstrap.min.js">
        </script>
        <script src="../js/userUpdate.js">
        </script>
        <script src="../js/userPageSideBar.js">
        </script>
        <script src="../vendor/sweetalert/js/sweetalert.min.js">
        </script>
    </body>
</html>
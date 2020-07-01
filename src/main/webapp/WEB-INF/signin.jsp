<%--
  Created by IntelliJ IDEA.
  User: Dennis Na
  Date: 2018. 3. 29.
  Time: PM 2:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>spin Contents Management System</title>

    <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/vendor/metismenu/css/metisMenu.min.css" rel="stylesheet" type="text/css">
    <link href="/vendor/sb-admin-2/css/sb-admin-2.min.css" rel="stylesheet" type="text/css">
    <link href="/vendor/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">

    <script src="/vendor/html5shiv/html5shiv.js"></script>
    <script src="/vendor/respond.js/respond.min.js"></script>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">관리자 로그인</h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="/signin" method="post">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="이메일" name="id" type="email" autofocus value="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="비밀번호" name="pw" type="password" value="">
                            </div>
                            <button class="btn btn-lg btn-success btn-block" type="submit">로그인</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.js"></script>
<script src="/vendor/metismenu/js/metisMenu.min.js"></script>
<script src="/vendor/sb-admin-2/js/sb-admin-2.min.js"></script>

</body>
</html>

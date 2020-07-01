<%--
  Created by IntelliJ IDEA.
  User: Dennis Na
  Date: 2018. 7. 24.
  Time: 오후 5:25
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
    <link href="/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/css/ad/log/report.css" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/profile.css" rel="stylesheet" type="text/css"/>

    <script src="/vendor/jquery/jquery.min.js"></script>
    <script src="/vendor/jquery/cookie.js"></script>
    <script src="/vendor/html5shiv/html5shiv.js"></script>
    <script src="/vendor/bootstrap/js/bootstrap.js"></script>
    <script src="/vendor/bootstrap-notify-master/bootstrap-notify.js"></script>
    <script src="/js/common.js"></script>
    <script src="/js/utils/date.js"></script>
    <script src="/js/utils/enums.js"></script>
    <script src="/js/admin/profile.js"></script>
</head>
<body>

<div id="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-brand" href="/" id="main-title">spin Contents Management System</a>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i>
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li>
                        <a href="/signout"><i class="fa fa-sign-out fa-fw"></i> 로그아웃</a>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>

    <div class="row ibox ibox-heading">
        <h2 class="text-center">회원님의 <span class="color-red">비밀번호를 변경</span>해주세요.</h2>
    </div>

    <form class="panel panel-default profile-form" style="max-width: 450px;" id="profileForm">
        <div class="panel-heading">
            <p>비밀번호 변경</p>
            <small>
                안전한 비밀번호로 내정보를 보호하세요.
                <ul>
                    <li><span class="color-red">다른아이디/사이트에서 사용한 적 없는 비밀번호</span></li>
                    <li><span class="color-red">이전에 사용한 적 없는 비밀번호</span>가 안전합니다.</li>
                </ul>
            </small>
        </div>
        <div class="panel-body">
            <div class="form-group password-field" id="passwordGroup">
                <input type="password" class="form-control" placeholder="새 비밀번호" id="newPw"/>
                <input type="password" class="form-control" placeholder="새 비밀번호 확인" id="confPw"/>
                <label class="control-label" for="confPw">입력된 비밀번호가 일치하지 않습니다.</label>
            </div>
        </div>
        <div class="panel-footer button-area">
            <button type="button" class="btn btn-default" id="btnCancel">취소</button>
            <button type="submit" class="btn btn-success" id="btnSave">확인</button>
        </div>
    </form>
</div>


</body>
</html>

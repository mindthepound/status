<%--
  Created by IntelliJ IDEA.
  User: Dennis Na
  Date: 2018. 3. 29.
  Time: PM 3:04
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

    <title>spin Contents Management System Signup</title>

    <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/vendor/metismenu/css/metisMenu.min.css" rel="stylesheet" type="text/css">
    <link href="/vendor/sb-admin-2/css/sb-admin-2.min.css" rel="stylesheet" type="text/css">
    <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="container">
    <form class="form-horizontal" id="signupForm" action="/signup">
        <div class="panel panel-primary">
            <div class="panel-heading">
                관리자 가입
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="name">이름</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="name" name="name" placeholder="이름을 입력하세요" type="text" value="">
                    </div>
                </div>
            </div>
            <div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="id">이메일</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="id" name="id" placeholder="이메일을 입력하세요" type="email" value="">
                    </div>
                </div>
            </div>
            <div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="pw">비밀번호</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="pw" name="pw" placeholder="비밀번호를 입력하세요" type="password"
                               value="">
                    </div>
                </div>
            </div>
            <div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="pwconfirm">비밀번호 확인</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="pwconfirm" name="pwconfirm" placeholder="비밀번호를 한번 더 입력하세요"
                               type="password" value="">
                    </div>
                </div>
            </div>
            <div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="confirm">확인 키</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="confirm" name="confirm" placeholder="확인 키를 입력 해 주세요"
                               type="password" value="">
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <input class="btn btn-primary pull-right" type="submit" value="가입">
                <div class="clearfix"></div>
            </div>
        </div>
    </form>
</div>

<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.js"></script>
<script src="/vendor/metismenu/js/metisMenu.min.js"></script>
<script src="/vendor/sb-admin-2/js/sb-admin-2.min.js"></script>
<script src="/js/signup.js"></script>

</body>
</html>

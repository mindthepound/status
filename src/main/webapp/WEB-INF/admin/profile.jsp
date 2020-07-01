<%--
  Created by IntelliJ IDEA.
  User: ihyeon-a
  Date: 2018. 7. 20.
  Time: 오후 6:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<t:template title="광고 로그 보고서" openNavClass="nav-5-4-1">
    <jsp:attribute name="header">
        <link href="/css/common.css" rel="stylesheet" type="text/css"/>
        <link href="/css/ad/log/report.css" rel="stylesheet" type="text/css"/>
        <link href="/css/admin/profile.css" rel="stylesheet" type="text/css"/>
    </jsp:attribute>
    <jsp:attribute name="javascriptSrc">
        <script src="/js/common.js"></script>
        <script src="/js/utils/date.js"></script>
        <script src="/js/utils/enums.js"></script>
        <script src="/js/admin/profile.js"></script>
    </jsp:attribute>
    <jsp:body>


        <div id="page-wrapper">
            <div class="row ibox ibox-heading">
                <h2 class="blue">Your Profile</h2>
            </div>

            <form class="panel panel-default profile-form" id="profileForm">
                <div class="panel-heading">
                    비밀번호 변경
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-xs-4 text-right">아이디</label>
                        <div class="col-xs-8">${result.id}</div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="form-group">
                        <label class="col-xs-4 text-right">이름</label>
                        <div class="col-xs-8">${result.name}</div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="form-group" id="passwordGroup">
                        <label class="col-xs-4 text-right">비밀번호</label>
                        <div class="col-xs-8 password-field">
                            <input type="password" class="form-control" placeholder="새 비밀번호" id="newPw"/>
                            <input type="password" class="form-control" placeholder="새 비밀번호 확인" id="confPw"/>
                            <label class="control-label" for="confPw">입력된 비밀번호가 일치하지 않습니다.</label>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="form-group">
                        <label class="col-xs-4 text-right">권한</label>
                        <div class="col-xs-8">${result.roles}</div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="form-group">
                        <label class="col-xs-4 text-right">마지막 비밀번호 변경일</label>
                        <div class="col-xs-8">${result.lastPasswordChange}</div>
                    </div>
                </div>
                <div class="panel-footer button-area">
                    <button type="button" class="btn btn-default" id="btnCancel">취소</button>
                    <button type="submit" class="btn btn-success" id="btnSave">확인</button>
                </div>
            </form>
        </div>
    </jsp:body>
</t:template>
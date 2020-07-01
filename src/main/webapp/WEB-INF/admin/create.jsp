<%--
  Created by IntelliJ IDEA.
  User: Dennis Na
  Date: 2018. 4. 23.
  Time: AM 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="io.spin.status.enumeration.Roles" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="관리자 생성" openNavClass="nav-1-1">
    <jsp:attribute name="header">
        <link href="/css/common.css" rel="stylesheet" type="text/css"/>
        <style type="text/css">
            .role-radio {
                display: inline-block;
                width: 30%;
                text-align: left;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="javascriptSrc">
        <script src="/js/utils/enums.js"></script>
        <script src="/js/common.js"></script>
        <script src="/js/admin/admin.js"></script>
        <script src="/js/admin/create.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <form id="signupForm">
                <div class="row">
                    <div class="col-md-12">
                        <h3 class="form-section">관리자 정보 등록
                            <div class="pull-right btn-pull-right">
                                <button type="button" class="btn btn-default" id="close">닫기</button>
                                <button type="submit" class="btn btn-success" id="createAdmin">저장</button>
                            </div>
                        </h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-bordered table-create table-tr-th-text-center">
                                <tr class="table-two-group-width">
                                    <th><i class="fa fa-exclamation color-red"></i> 이메일</th>
                                    <td>
                                        <input type="email" class="form-control" id="id" name="id"
                                               placeholder="이메일을 입력하세요">
                                    </td>
                                    <th><i class="fa fa-exclamation color-red"></i> 이름</th>
                                    <td>
                                        <input type="text" class="form-control" id="name" name="name"
                                               placeholder="이름을 입력하세요">
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation color-red"></i> 비밀번호</th>
                                    <td>
                                        <input type="password" class="form-control" id="pw" name="pw"
                                               placeholder="비밀번호를 입력하세요">
                                    </td>
                                    <th><i class="fa fa-exclamation color-red"></i> 비밀번호 확인</th>
                                    <td>
                                        <input type="password" class="form-control" id="pwconfirm" name="pwconfirm"
                                               placeholder="비밀번호를 다시 한번 입력하세요">
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation color-red"></i> 권한</th>
                                    <td class="text-center">
                                        <div class="checkbox role-radio">
                                            <label>
                                                <input type="radio" name="roleGroup" value="${Roles.ADMIN}" checked> 일반관리자
                                            </label>
                                        </div>
                                        <div class="checkbox role-radio">
                                            <label>
                                                <input type="radio" name="roleGroup" value="${Roles.AD}"> 협력업체
                                            </label>
                                        </div>
                                        <div class="checkbox role-radio">
                                            <label>
                                                <input type="radio" name="roleGroup" value="${Roles.MOVIGAME}"> 모비게임
                                            </label>
                                        </div>
                                    </td>
                                    <th>슈퍼 관리자</th>
                                    <td>
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" id="super" name="super" value="${Roles.SUPER}"> 슈퍼관리자
                                            </label>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </jsp:body>
</t:template>

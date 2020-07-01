<%--
  Created by IntelliJ IDEA.
  User: spin
  Date: 2018. 4. 2.
  Time: AM 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="io.spin.status.enumeration.Roles" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<t:template title="관리자 관리" openNavClass="nav-1-1">
    <jsp:attribute name="header">
        <link href="/css/common.css" rel="stylesheet" type="text/css"/>
        <link href="/css/admin/profile.css" rel="stylesheet" type="text/css"/>
    </jsp:attribute>
    <jsp:attribute name="javascriptSrc">
        <script src="/vendor/bootbox/bootbox.min.js"></script>
        <script src="/js/common.js"></script>
        <script src="/js/utils/enums.js"></script>
        <script src="/js/admin/admin.js"></script>
        <script src="/js/admin/list.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <div class="row ibox ibox-heading">
                <h2 class="snb-title"><i class="fa fa-user-secret"></i> <a href="/admin/list">관리자 관리</a></h2>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <span class="table-total">
                        <strong>Count/Scanned : ${result.pageResult.count}/${result.pageResult.scannedCount}</strong>
                    </span>
                    <a href="/admin/create" type="button" class="btn btn-info table-button" id="adminCreate" style="float: right;">관리자 추가</a>
                </div>

                <div class="col-xs-12">
                    <table class="table table-hover table-list">
                        <thead class="table-tr-th-text-center">
                        <tr>
                            <th width="20%">Email</th>
                            <th width="20%">이름</th>
                            <th width="20%">권한</th>
                            <th width="25%">마지막 비밀번호 변경일</th>
                            <th width="15%">편집</th>
                        </tr>
                        </thead>
                        <tbody class="table-tr-td-text-center">
                        <c:forEach var="item" items="${result.pageResult.results}" varStatus="status">
                            <tr name="list">
                                <td name="email">${item.id}</td>
                                <td name="name">${item.name}</td>
                                <td name="role">${item.roles}</td>
                                <td name="lastPasswordChange">${fn:substring(item.lastPasswordChange, 0, 10)}</td>
                                <td name="edit">
                                    <c:if test="${fn:contains(cookie.TYPE.value, 'ROLE_SUPER')}">
                                        <button type="button" class="btn btn-default editAdmin" data-toggle="modal" data-target="#changePwModal" data-id="${item.id}" data-name="${item.name}" data-roles="${item.roles}" data-last-changed-pw="${item.lastPasswordChange}">Edit</button>
                                        <button type="button" class="btn btn-danger removeAdmin" name="remove"><i class="glyphicon glyphicon-trash"></i></button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- change password modal -->
            <div class="modal fade" id="changePwModal" style="margin-top: 150px;">
                <div class="modal-dialog">
                    <div class="modal-content profile-form">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">비밀번호 변경</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label class="col-xs-4 text-right">아이디</label>
                                <div class="col-xs-8" id="modalField-id"></div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="form-group">
                                <label class="col-xs-4 text-right">이름</label>
                                <div class="col-xs-8" id="modalField-name"></div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="form-group" id="passwordGroup">
                                <label class="col-xs-4 text-right">비밀번호</label>
                                <div class="col-xs-8 password-field">
                                    <input type="password" class="form-control" placeholder="새 비밀번호" id="modalField-newPw"/>
                                    <input type="password" class="form-control" placeholder="새 비밀번호 확인" id="modalField-confPw"/>
                                    <label class="control-label" for="modalField-confPw">입력된 비밀번호가 일치하지 않습니다.</label>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="form-group">
                                <label class="col-xs-4 text-right">권한</label>
                                <div class="col-xs-8">
                                    <div class="checkbox" style="display: block;">
                                        <label><input type="radio" name="roleGroup" value=""> 권한없음</label>
                                    </div>
                                    <div class="checkbox">
                                        <label><input type="radio" name="roleGroup" value="${Roles.ADMIN}"> 일반관리자</label>
                                    </div>
                                    <div class="checkbox">
                                        <label><input type="radio" name="roleGroup" value="${Roles.AD}"> 협력업체</label>
                                    </div>
                                    <div class="checkbox">
                                        <label><input type="radio" name="roleGroup" value="${Roles.MOVIGAME}"> 모비게임</label>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="form-group">
                                <label class="col-xs-4 text-right">슈퍼 관리자</label>
                                <div class="col-xs-8">
                                    <div class="checkbox">
                                        <label><input type="checkbox" id="modalField-roleSuper"> 슈퍼관리자</label>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="form-group">
                                <label class="col-xs-4 text-right">마지막 비밀번호 변경일</label>
                                <div class="col-xs-8" id="modalField-lastChangedPw"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary" id="save">Save changes</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </div>
    </jsp:body>
</t:template>
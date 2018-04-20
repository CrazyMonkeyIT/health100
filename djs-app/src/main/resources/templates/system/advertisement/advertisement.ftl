<@ui.layout >
<script src="${request.contextPath}/static/js/jquery-1.11.0.js"></script>
<script src="${request.contextPath}/static/js/jquery.form.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/advertisement/advertisement.js" ></script>

<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
    <div class="row">
        <form class="form-inline" id="form1" role="form" action="${request.getContextPath()}/system/user/list" method="post">
            <a onclick="showAddModal()" class="btn btn-white btn-info">
                <i class="glyphicon glyphicon-plus"></i>
                新增广告
            </a>
        </form>
    </div>

    <table class="table table-striped table-bordered table-hover dataTable no-footer" >
        <thead class="thin-border-bottom">
            <tr >
                <th >序号</th>
                <th >广告标题</th>
                <th >广告描述</th>
                <th >广告类型</th>
                <th >广告连接</th>
                <th >广告图片</th>
                <th >到期时间</th>
                <th >广告状态</th>
                <th >操作</th>
            </tr>
        </thead>
        <tbody>
            <#list page.list as data>
            <tr>
                <td>${((page.pageNum-1) * 10) + (data_index+1)}</td>
                <td>${data.advertisementTitle!''}</td>
                <td>${data.advertisementDesc!''}</td>
                <td>
                    <#if data.advertisementTypeId == 1>
                    超链接广告
                    <#elseif data.advertisementTypeId == 2>
                    路演主题广告
                    <#elseif data.advertisementTypeId == 3>
                    专题广告
                    <#elseif data.advertisementTypeId == 4>
                    讲师广告
                    <#elseif data.advertisementTypeId == 5>
                    指定路演广告
                    <#elseif data.advertisementTypeId == 6>
                    VIP广告
                    <#else>
                    未知广告类型
                    </#if>
                </td>
                <td>${data.advertisementUrl!''}</td>
                <td style="width: 100px; height: 25px">
                    <a href="${data.advertisementImgPath}" target="_blank" >
                        <img src="${data.advertisementImgPath}" style="width: 100px; height: 25px">
                    </a>
                </td>
                <td>${data.invalidDate?string('yyyy-MM-dd')}</td>
                <td>
                    <#if data.status == 1>
                        正常
                    <#elseif data.status == -1>
                        到期
                    <#elseif data.status == -2>
                        人工暂停
                    </#if>
                </td>
                <td>
                    <#if data.status == 1>
                    <a onclick="stopAdvertisement(${data.advertisementId})">
                        暂停
                    </a>
                    <a onclick="showEditModal(${data.advertisementId})" class="btn btn-white btn-primary btn-bold"  data-rel="tooltip" title="" data-original-title="修改" title="修改">
                        <i class="fa fa-pencil bigger-110 green" ></i>
                    </a>
                    </#if>
                    <a onclick="delAdvertisement(${data.advertisementId})" class="btn btn-white btn-primary btn-bold" data-rel="tooltip" title="" data-original-title="删除" title="删除">
                        <i class="fa fa-trash-o bigger-110 red"></i>
                    </a>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>

<div id="edit_grade_modal" class="modal fade" style="display: none; " data-backdrop="static" role="dialog" tabindex="-1" class="modal fade in exam_newbox">
    <div class="modal-dialog">
        <div class="modal-content"  >
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4 class="blue"><i class="fa fa-pencil"></i>编辑广告</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" action="${request.getContextPath()}/advertisement/edit" method="post">
                    <div class="form-horizontal">
                        <input type="hidden" name="advertisementId" />

                        <div class="form-group ">
                            <label class="col-sm-4 control-label">广告标题</label>
                            <div class="col-sm-8">
                                <input name="advertisementTitle" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">广告描述</label>
                            <div class="col-sm-8">
                                <input name="advertisementDesc" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">到期时间</label>
                            <div class="col-sm-8">
                                <input name="invalidDate" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">广告类型</label>
                            <div class="col-sm-8">
                                <select id="advertisementType">
                                    <option value="-1">
                                        --请选择--
                                    </option>
                                    <#list typeList as type>
                                        <option value="${type.advertisementTypeId!''}">
                                        ${type.advertisementTypeDesc!''}
                                        </option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <!-- 动态显示DIV开始 -->
                        <div id="urlDiv" class="form-group " style="display: none">
                            <label class="col-sm-4 control-label">广告连接</label>
                            <div class="col-sm-8">
                                <input name="advertisementUrl" type="text"  />
                            </div>
                        </div>
                        <div id="roomDiv" class="form-group " style="display: none">
                            <label class="col-sm-4 control-label">关联房间</label>
                            <div class="col-sm-8">
                                <select id="room">
                                    <option value="-1">
                                        --请选择--
                                    </option>
                                    <#list roomList as room>
                                        <option value="${room.id!''}">
                                        ${room.name!''}
                                        </option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div id="lecturerDiv" class="form-group " style="display: none">
                            <label class="col-sm-4 control-label">关联讲师</label>
                            <div class="col-sm-8">
                                <select id="lecturer">
                                    <option value="-1">
                                        --请选择--
                                    </option>
                                    <#list lecturerList as lecturer>
                                        <option value="${lecturer.id!''}">
                                        ${lecturer.lecturerName!''}
                                        </option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <!-- 动态显示DIV结束 -->

                        <input id="advertisementImgPath" type="hidden" name="advertisementImgPath" />
                        <input id="advertisementTypeId" type="hidden" name="advertisementTypeId">
                    </div>
                </form>
                <div class="form-horizontal">
                    <div class="form-group ">
                        <label class="col-sm-4 control-label">广告图片</label>
                        <div class="col-sm-8">
                            <form id="imgForm" action="${request.getContextPath()}/import/up/advertisement" method="post" enctype="multipart/form-data" >
                                <input type="file" name="file" value="请选择广告图片">
                                <input id="upFileBtn" type="button" value="上传">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a onclick="saveAdvertisementInfo()" class="btn btn-white btn-info btn-bold">
                    <i class="ace-icon glyphicon glyphicon-ok blue"></i>
                    保存
                </a>
                <a class="btn btn-white btn-info btn-bold" data-dismiss="modal">
                    <i class="ace-icon glyphicon glyphicon-remove blue"></i>
                    取消
                </a>
            </div>
        </div>
    </div>
</div>

</@ui.layout>
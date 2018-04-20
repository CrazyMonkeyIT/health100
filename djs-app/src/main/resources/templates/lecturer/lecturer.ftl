<@ui.layout >
<!-- zTree -->
<link rel="stylesheet" href="${request.contextPath}/static/ztree/zTreeStyle.css" type="text/css">
<script src="${request.contextPath}/static/ztree/jquery.ztree.all-3.5.js"></script>
<!-- My97 -->
<script src="${request.contextPath}/static/My97DatePicker/WdatePicker.js"></script>
<div class="col-xs-12">
    <!-- div.table-responsive -->

    <!-- div.dataTables_borderWrap -->

    <div>
        <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
            <div class="row">
                <form class="form-inline" id="form1" role="form" action="${request.getContextPath()}/lecturer/list" method="post">
                    <input id="pageIndex" name="pageIndex" value="${page.pageNum}" type="hidden" />
                    <span >讲师姓名：</span>
                    <input type="text" name="lecturerName" value="${lecturerName!}" class="form-control" >
                    <span >手机号：</span>
                    <input type="text" name="phone" value="${phone!}" class="form-control" >
                    &nbsp;
                    <button type="submit" class="btn btn-white btn-info">
                        <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                        查询
                    </button>
                </form>
            </div>
                <table class="table table-striped table-bordered table-hover dataTable no-footer" >
                    <thead class="thin-border-bottom">
                    <tr >
                        <th >序号</th>
                        <th ><i class="normal-icon ace-icon fa fa-user"></i>讲师姓名</th>
                        <th >手机号</th>
                        <th >公司</th>
                        <th >职位</th>
                        <th >状态</th>
                        <th ><i class="normal-icon ace-icon fa fa-clock-o"></i>创建时间</th>
                        <th ><i class="ace-icon fa fa-wrench"></i>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#if page.list?? && (page.list?size > 0)>
                            <#list page.list as data>
                            <tr>
                                <td>${((page.pageNum-1) * 10) + (data_index+1)}</td>
                                <td>
                                    <#if (data.isChief == 'YES')>
                                        <span class="label label-purple label-white middle">首席</span>
                                    </#if>
                                    <span class="blue">${data.lecturerName!''}</span>
                                </td>
                                <td>${data.phone!''}</td>
                                <td>${data.company!''}</td>
                                <td>${data.position!''}</td>
                                <td>
                                    <#if data.status == 0><span class="label label-success label-white middle">正常</span></#if>
                                    <#if data.status == 1><span class="label label-yellow label-white middle">直播中</span></#if>
                                    <#if data.status == 2><span class="label label-white middle">封停</span></#if>
                                </td>
                                <td><#if data.createTime??>${(data.createTime?string('yyyy-MM-dd HH:mm:ss'))}</#if></td>
                                <td >
                                    <div class="btn-overlap btn-group">

                                        <a onclick="showUpdate(${data.id})" class="btn btn-sm btn-white btn-primary" >
                                            <i class="fa fa-pencil bigger-115 green"></i>
                                            修改
                                        </a>

                                        <a onclick="showFrozenModel(${data.id})" class="btn btn-sm btn-white btn-primary" >
                                            <#if (data.status == 0 || data.status == 1)>
                                                <i class="fa fa-lock bigger-115 orange"></i>
                                                封停
                                            <#elseif (data.status == 2)>
                                                <i class="fa fa-unlock bigger-115 orange"></i>
                                                解封
                                            </#if>
                                        </a>

                                        <a onclick="setChief(${data.id})" class="btn btn-sm btn-white btn-primary" >
                                             <#if (data.isChief == 'NO')>
                                                 <i class="fa fa-eye bigger-110 purple"></i>
                                                 置为首席
                                             <#elseif (data.isChief == 'YES')>
                                                 <i class="fa  fa-eye-slash bigger-110 purple"></i>
                                                 取消首席
                                             </#if>
                                        </a>

                                        <a onclick="showAccount(${data.id})" class="btn btn-sm btn-white btn-primary" >
                                            <i class="fa fa-credit-card bigger-110 grey"></i>
                                            账户
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            </#list>
                        <#else>
                        <tr style="text-align:center;">
                           <td colspan="9"><h4 class="green">暂无记录</h4></td>
                        </tr>
                        </#if>
                    </tbody>
                </table>
            <div class="row">
                <!-- 分页begin -->
                <#if (page.pages>0)>
                    <#import "../macro/pagination.ftl" as cast/>
                    <@cast.pagination callFunName="submitForm" />
                </#if>
                <!-- 分页end -->
            </div>
        </div>
    </div>
</div>
<!-- 编辑讲师信息begin -->
<div id="edit_modal" class="modal fade" style="display: none; " data-backdrop="static" role="dialog" tabindex="-1" class="modal fade in exam_newbox">
    <div class="modal-dialog">
        <div class="modal-content"  >
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4 class="blue"><i class="fa fa-pencil"></i>&nbsp;讲师信息</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" action="${request.getContextPath()}/lecturer/update" method="post">
                    <div class="form-horizontal">
                        <!-- 讲师ID -->
                        <input type="hidden" name="id" />

                        <div class="form-group ">
                            <label class="col-sm-4 control-label">姓名</label>
                            <div class="col-sm-8">
                                <input name="lecturerName" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">手机号</label>
                            <div class="col-sm-8">
                                <input name="phone" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">讲师等级</label>
                            <div class="col-sm-8">
                                <select name="gradeId">
                                    <#list gradeList as grade>
                                        <option value="${grade.lecturerGradeId}">${grade.gradeName}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">公司</label>
                            <div class="col-sm-8">
                                <input name="company" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">职位</label>
                            <div class="col-sm-8">
                                <input name="position" type="text"  />
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a onclick="updateInfo()" class="btn btn-white btn-info btn-bold">
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
<!-- 编辑讲师信息end -->
<!-- 账户信息begin -->
<div id="account_modal" class="modal fade" style="display: none; " data-backdrop="static" role="dialog" tabindex="-1" class="modal fade in exam_newbox">
    <div class="modal-dialog">
        <div class="modal-content"  >
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4 class="blue"><i class="fa fa-pencil"></i>&nbsp;账户信息</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <!-- 账户ID -->
                    <input type="hidden" id="accountId" />

                    <div class="form-group ">
                        <label class="col-sm-4 control-label">总收益：</label>
                        <div class="col-sm-8">
                            <label id="totalIncome" style="line-height: 32px;"></label>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label class="col-sm-4 control-label">分成比例：</label>
                        <div class="col-sm-8">
                            <label id="paymentRatio" style="line-height: 32px;"></label>%
                        </div>
                    </div>
                    <div class="form-group ">
                        <label class="col-sm-4 control-label">可提现金额：</label>
                        <div class="col-sm-8">
                            <label id="withdrawCash" style="line-height: 32px;"></label>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label class="col-sm-4 control-label">是否可提现：</label>
                        <div class="col-sm-8" >
                            <select id="withdrawSwitch" onChange="withdrawSwitchChange()">
                                <option value="ON">可提现</option>
                                <option value="OFF">不可提现</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group " id="withdrawOffDescDiv">
                        <label class="col-sm-4 control-label">不可提现原因：</label>
                        <div class="col-sm-8">
                            <textarea id="withdrawOffDesc" rows="5" cols="30"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a onclick="saveWithdrawSwitch()" class="btn btn-white btn-info btn-bold">
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
<!-- 账户信息end -->
<!-- 封停账户begin -->
<div id="frozen_modal" class="modal fade" style="display: none; " data-backdrop="static" role="dialog" tabindex="-1" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content" style="width:400px;margin:0px auto;" >
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4 class="blue"><i class="fa fa-list"></i>&nbsp;请填写（封停/解封）原因</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="frozenRecturerId" />
                <textarea id="frozenDescrible" rows="7" cols="50"></textarea>
            </div>
            <div class="modal-footer">
                <button onclick="updateStatus()" class="btn btn-white btn-info btn-bold">
                    <i class="ace-icon glyphicon glyphicon-ok blue"></i>
                    确定
                </button>
                <button class="btn btn-white btn-info btn-bold" data-dismiss="modal">
                    <i class="ace-icon glyphicon glyphicon-remove blue"></i>
                    取消
                </button>
            </div>
        </div>
    </div>
</div>
<!-- 封停账户end -->
<script>
jQuery(function($) {
    $('[data-rel=tooltip]').tooltip();
});
// 分页查询
function submitForm(index){
    $("#pageIndex").val(index);
    $("#form1").submit();
}
/**
 * 显示封停账户
 */
function showFrozenModel(id){
    $("#frozenRecturerId").val(id);
    $("#frozen_modal").modal("show");
}
/**
 * 封停/解封
 */
function updateStatus(){
    var frozenRecturerId = $("#frozenRecturerId").val();
    var frozenDescrible = $("#frozenDescrible").val();
    $.ajax({
        url : basePath+"/lecturer/frozenLecturer",
        type : 'post',
        dataType:'json',
        data : {
            "id":frozenRecturerId,
            "desc":frozenDescrible
        },
        success : function(data) {
            if(data.result){
                $("#form1").submit();
            }else{
                alert(data.message);
            }
        }
    });
}

/**
 * 设置（是/否）首席
 */
function setChief(id){
    $.ajax({
        url : basePath+"/lecturer/recommendChief",
        type : 'post',
        dataType:'json',
        data : {
            "id":id
        },
        success : function(data) {
            if(data.result){
                $("#form1").submit();
            }else{
                alert(data.message);
            }
        }
    });
}

function showUpdate(id){
    $.ajax({
        url : basePath+"/lecturer/get",
        type : 'post',
        dataType:'json',
        data : {
            id:id
        },
        success : function(data) {
            if(!!data){
                $('#editForm')[0].reset();
                $("#editForm").find("input[name='id']").val(data.id);
                $("#editForm").find("input[name='lecturerName']").val(data.lecturerName);
                $("#editForm").find("input[name='phone']").val(data.phone);
                $("#editForm").find("select[name='gradeId']").val(data.gradeId);
                $("#editForm").find("input[name='company']").val(data.company);
                $("#editForm").find("input[name='position']").val(data.position);
                $("#edit_modal").modal("show");
            }
        }
    });
}

function updateInfo(){
    $.ajax({
        url : $("#editForm").attr("action"),
        type : 'post',
        dataType:'json',
        data : $("#editForm").serialize(),
        success : function(data) {
            if(data.result){
                $("#form1").submit();
            }else{
                alert(data.msg);
            }
        }
    });
}

function showAccount(id){
    console.log(id);
    $.ajax({
        url : basePath + "/lecturer/account",
        type : 'post',
        dataType:'json',
        data : {
            lecturerId:id
        },
        success : function(data) {
            if(!!data){
                $("#accountId").val(data.id);
                $("#totalIncome").html(data.totalIncome);
                $("#paymentRatio").html(data.paymentRatio);
                $("#withdrawCash").html(data.withdrawCash);
                $("#withdrawSwitch").val(data.withdrawSwitch);
                $("#withdrawOffDesc").html(data.withdrawOffDesc);
                withdrawSwitchChange();
                $("#account_modal").modal("show");
            }
        }
    });
}

function withdrawSwitchChange(){
    var withdrawSwitch = $("#withdrawSwitch").val();
    if("OFF" == withdrawSwitch){
        $("#withdrawOffDescDiv").show();
    }else{
        $("#withdrawOffDescDiv").hide();
    }
}

function saveWithdrawSwitch(){
    var accountId = $("#accountId").val();
    var withdrawSwitch = $("#withdrawSwitch").val();
    var withdrawOffDesc = $("#withdrawOffDesc").val();
    $.ajax({
        url : basePath + "/lecturer/frozenLecturerAccount",
        type : 'post',
        dataType:'json',
        data : {
            accountId:accountId,
            wswitch:withdrawSwitch,
            desc:withdrawOffDesc
        },
        success : function(data) {
            if(data.result){
                alert("操作成功");
            }else{
                alert(data.message);
            }
        }
    });
}
</script>
</@ui.layout>
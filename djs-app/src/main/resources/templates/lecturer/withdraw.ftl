<@ui.layout >
<div class="col-xs-12">

    <div>
        <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
            <div class="row" style="display: none;">
                <form class="form-inline" id="form1" role="form" action="${request.getContextPath()}/lecturer/withdraw/list" method="post">
                    <input id="pageIndex" name="pageIndex" value="${page.pageNum}" type="hidden" />
                </form>
            </div>

            <table class="table table-striped table-bordered table-hover dataTable no-footer" >
                <thead class="thin-border-bottom">
                <tr >
                    <th >序号</th>
                    <th >讲师姓名</th>
                    <th >提现金额</th>
                    <th >状态</th>
                    <th ><i class="normal-icon ace-icon fa fa-clock-o"></i>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#if page.list?? && (page.list?size > 0)>
                        <#list page.list as data>
                        <tr>
                            <td>${((page.pageNum-1) * 10) + (data_index+1)}</td>
                            <td>${data.lecturerName!''}</td>
                            <td>${data.withdrawMoney!''}</td>
                            <td>
                                <#if data.status == 'WAIT'>
                                    <span class="label label-yellow label-white middle">等待审核</span>
                                </#if>
                                <#if data.status == 'ALREADY'>
                                    <span class="label label-success label-white middle">已通过</span>
                                </#if>
                                <#if data.status == 'REFUSE'>
                                    <span class="label label-white middle">已拒绝</span>
                                </#if>
                            </td>
                            <td><#if data.createTime??>${(data.createTime?string('yyyy-MM-dd HH:mm:ss'))}</#if></td>
                            <td>
                                <#if data.status?? >
                                <#else >
                                    <div class="btn-overlap btn-group">
                                        <a onclick="examine(${data.id},'ALREADY')" class="btn btn-sm btn-white btn-primary" >
                                            通过
                                        </a>
                                        <a onclick="examine(${data.id},'REFUSE')" class="btn btn-sm btn-white btn-primary" >
                                            拒绝
                                        </a>
                                    </div>
                                </#if>
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

<script>
    jQuery(function($) {

    });
    // 分页查询
    function submitForm(index){
        $("#pageIndex").val(index);
        $("#form1").submit();
    }

    function examine(id,handleResult){
        $.ajax({
            url : basePath+"/lecturer/withdraw/examine",
            type : 'post',
            dataType:'json',
            data : {
                id:id,
                handleResult:handleResult
            },
            success : function(data) {
                if(data.result){
                    $("#form1").submit();
                }else{
                    alert(data.msg);
                }
            }
        });
    }

</script>
</@ui.layout>
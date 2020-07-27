<div class="card" style="position:absolute;width:${width}px;height:${height}px;left:${x}px;top:${y}px">
    <div class="card-header" style="width:${width}px;height:50px;font-size: 30px;text-align: center">
        ${header}
    </div>
    <div class="card-body">
        <#if content?has_content>
            ${content}
        </#if>
    </div>
</div>

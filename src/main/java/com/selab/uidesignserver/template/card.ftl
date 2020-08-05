<div class="card" style="position:absolute;width:${width}px;height:${height}px;left:${x}%;top:${y}%">
    <div class="card-header" style="width:${width}px;height:50px;font-size: 30px;text-align: center;padding: 0px;">
        ${header}
    </div>
    <div class="card-body">
        <#if content?has_content>
            ${content}
        </#if>
    </div>
</div>

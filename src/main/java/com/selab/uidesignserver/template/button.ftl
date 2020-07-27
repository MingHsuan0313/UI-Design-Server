<#if href?has_content>
    <#if href != "">
        <a class="btn btn-info" href="${href}" role="button" style="position:absolute;width:${width}px;height:${height}px;left:${x}px;top:${y}px;font-size:16px">${text}</a>
    </#if>
<#else>
    <button type="button" class="btn btn-info" style="position:absolute;width:${width}px;height:${height}px;left:${x}px;top:${y}px;font-size:16px">${text}</button>
</#if>




<#if isCompositeElement=="true">

    <#if href?has_content>
        <#if href != "">
            <button type="button" class="btn btn-info" routerLink="${href}"
                    style="position:absolute;width:${width}px;height:${height}px;left:${x}px;top:${y}px;font-size:16px">${text}</button>
        </#if>
    <#else>
        <button type="button" class="btn btn-info"
                style="position:absolute;width:${width}px;height:${height}px;left:${x}px;top:${y}px;font-size:16px">${text}</button>
    </#if>

<#else>

    <#if href?has_content>
        <#if href != "">
            <button type="button" class="btn btn-info" routerLink="${href}"
                    style="position:absolute;width:${width}px;height:${height}px;left:${x}%;top:${y}%;font-size:16px">${text}</button>
        </#if>
    <#else>
        <button type="button" class="btn btn-info"
                style="position:absolute;width:${width}px;height:${height}px;left:${x}px;top:${y}px;font-size:16px">${text}</button>
    </#if>
</#if>


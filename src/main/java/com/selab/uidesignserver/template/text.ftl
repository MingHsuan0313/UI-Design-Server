<#if isCompositeElement=="true">
    <a style="position:absolute;left:${x}px;top:${y}px;font-size:${fontSize}px"
            <#if href?has_content>
                routerLink="${href}"
            </#if>
    >
        <#if text?has_content>
            ${text}
        </#if>
    </a>
<#else>
    <a style="position:absolute;left:${x}%;top:${y}%;font-size:${fontSize}px"
            <#if href?has_content>
                routerLink="${href}"
            </#if>
    >
        <#if text?has_content>
            ${text}
        </#if>
    </a>
</#if>




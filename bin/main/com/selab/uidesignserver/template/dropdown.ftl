<#if isCompositeElement=="true">
    <div class="dropdown"  style="position:absolute;width:${width}px;height:${height}px;left:${x}px;top:${y}px;font-size:16px">
        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        </a>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
            <#list items as item>
                <a class="dropdown-item" href="#">${item}</a>
            </#list>
        </div>
    </div>
<#else >
    <div class="dropdown"  style="position:absolute;width:${width}px;height:${height}px;left:${x}%;top:${y}%;font-size:16px">
        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        </a>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
            <#list items as item>
                <a class="dropdown-item" href="#">${item}</a>
            </#list>
        </div>
    </div>
</#if>

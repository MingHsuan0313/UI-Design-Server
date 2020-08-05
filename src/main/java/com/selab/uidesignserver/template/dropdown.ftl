<div class="dropdown"  style="position:absolute;width:${width}px;height:${height}px;left:${x}%;top:${y}%;font-size:20px">
    <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    </a>
    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
        <#list items as item>
            <a class="dropdown-item" href="#">${item}</a>
        </#list>
    </div>
</div>

<#-- @ftlvariable name="products" type="kotlin.collections.List<com.espoletatecnologias.api.modules.warehouse.products.domain.models.Product>" -->
<#import "../../_layout.ftl" as layout>
<@layout.header>
    <div>
        <table>
            <tr style="gap: 1rem">
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
            </tr>
            <tr>
                <form action="/warehouse/products/ui" method="get">
                    <td><input type="text" name="w_id" style="width: 90%"></td>
                    <td><input type="text" name="w_name"></td>
                    <td><input type="text" name="w_description"></td>
                    <td><input type="submit" value="Search"></td>
                </form>
            </tr>
            <#list products as product>
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.description}</td>
                </tr>
            </#list>
        </table>
    </div>
</@layout.header>

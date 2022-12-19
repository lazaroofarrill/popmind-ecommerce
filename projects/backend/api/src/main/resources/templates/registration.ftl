<#-- @ftlvariable name="flow" type="String" -->
<#-- @ftlvariable name="response" type="com.espoletatecnologias.api.modules.iam.services.KratosClient.SelfServiceApiResponse" -->
<#import "_layout.ftl" as layout>
<@layout.header>
    <div>
        <h1>Registration page</h1>
        ${response.id}

        <form action="${response.ui.action}" method="post">
            <#list response.ui.nodes as inputs>
                <div>

                    <label for="${inputs.attributes.name}">
                        <#if inputs.attributes.type != "hidden">
                            ${inputs.attributes.name}
                        </#if>
                        <input name="${inputs.attributes.name}"
                               type="${inputs.attributes.type}"/>
                    </label>
                </div>
            </#list>
        </form>
    </div>
</@layout.header>

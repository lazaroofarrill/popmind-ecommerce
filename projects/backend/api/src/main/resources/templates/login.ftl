<#-- @ftlvariable name="name" type="String" -->
<#import "_layout.ftl" as layout>
<@layout.header>
    <div class="flex items-center flex-col">
        <div class="text-center flex items-center flex-col gap-4">
            <h1 class="text-3xl text-primary underline font-bold">IAM</h1>
            <div>Or am I ${name}?</div>
            <form action="/iam/login" method="post" class="flex flex-col ga gap-4">
                <div><input class="bg-gray-50 border-2 border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" type="text" name="username" placeholder="Username"/>
                </div>
                <div>
                    <input
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                            type="password" name="password"
                            placeholder="Password"/></div>
                <div>
                    <input class="btn drop-shadow-md" type="submit"/>
                </div>
            </form>
        </div>
    </div>
</@layout.header>

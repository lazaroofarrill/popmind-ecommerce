<#macro header>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title>IAM Module</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <script>
            tailwind.config = {
                theme: {
                    extend: {
                        colors: {
                            clifford: '#da373d',
                            primary: '#FA8072',
                            accent: '#87D9C8',
                            background: '#202A44'
                        }
                    }
                }
            }
        </script>
        <link rel="stylesheet" href="/styles.css" type="text/css">
        <style type="text/tailwindcss">
            @layer components {
                body {
                    /*@apply bg-background text-white;*/
                }

                .btn {
                    @apply bg-primary text-white font-bold py-2 px-4 rounded;
                }
            }
        </style>
    </head>
    <body>
    <div class="flex flex-col items-center" style="padding-top: 64px">
        <#nested>

        <div style="border-top: solid 2px white; margin-top: 16px; padding-top: 16px">
            <a href="/">Back home</a>
        </div>
    </div>
    </body>
    </html>
</#macro>

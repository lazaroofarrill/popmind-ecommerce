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
                    @apply bg-background text-white;
                }

                .btn {
                    @apply bg-primary text-white font-bold py-2 px-4 rounded;
                }
            }
        </style>
    </head>
    <body>
    <#nested>
    </body>
    </html>
</#macro>

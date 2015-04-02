Get information:
    url = "bama.ua.edu/~cppopovich/CS495/request.php"
    post:
        'tbl' is one of (account,textbook,ticket,sublease)
        'args' is a comma-separated list of conditions.
        example: 'subject=CS,price>9,price<20'
        'sort' is the condition to sort by, prefaced by + or -
        example: '+price'

Create info:
    url = "bama.ua.edu/~cppopovich/CS495/add.php"
    post:
        'tbl' is one of (account,textbook,ticket,sublease)
        'args' is a pipe-separated list of data (pipe='|')
        Alternate name|data|name|data etcetera
        example: 'subject|CS|number|495'

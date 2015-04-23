Get information:
    url = "bama.ua.edu/~cppopovich/CS495/request.php"
    post:
        'tbl' is one of (account,textbook,ticket,sublease)
        'args' is a comma-separated list of conditions.
        For my convenience, the first value in each pair is assumed to be the field. The second is assumed to be the value.
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

Edit info:
    url = "bama.ua.edu/~cppopovich/CS495/edit.php"
    post:
        'tbl' is one of (account,textbook,ticket,sublease)
        'key' is the id # of the listing or the name of the account
        'args' is a pipe-separated list of data to change (pipe='|')
        Only the data to be modified needs to be included.

Delete info:
    use "Edit info" with 'args' as "del|1"
    This sets the deleted flag for the listing.

Column Data:
    For getCourseNumbers(), etc.
    url = "bama.ua.edu/~cppopovich/CS495/add.php"
    post:
        'tbl' is one of (account,textbook,ticket,sublease)
        'col' is the column of the table you want
        example: 'textbook' and 'subject' returns every subject
            that there is a listing for.

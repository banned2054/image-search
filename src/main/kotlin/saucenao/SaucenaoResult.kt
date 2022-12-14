package saucenao

class SaucenaoResult
{
    class SaucenaoRaw constructor(var url : String, var thumbnail : String)
    {
    
    }
    
    var items = mutableListOf<SaucenaoRaw>()
}
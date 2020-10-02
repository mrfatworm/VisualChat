package yuntech.b10517012.visualchat.model

class WordModel {
    var id: Long = 0
    var sentence: String = ""
    var visible: Int = 1
    var order: Int = 0

    constructor(id: Long, sentence: String, order: Int){
        this.id = id
        this.sentence = sentence
        this.order = order
    }
    constructor()
}
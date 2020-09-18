package yuntech.b10517012.visualchat.model

class WordModel {
    var id: Int
    var sentence: String = ""

    constructor(id: Int, sentence: String){
        this.id = id
        this.sentence = sentence
    }

}
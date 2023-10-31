const QuestionList = [
    {
        question: "do you eat meat?",
        answers: {
            a: "yes",
            b: "no",
        }
    },
    {
        question: "what vehicle do you use?",
        answers: {
            a:"car",
            b:"bike",
            c:"public transport"
        }
    }
]
addEventListener("click", changeQuestion, true);
var index = 0
function changeQuestion(){
    
    const everythings = QuestionList[index]
    
    if (index < QuestionList.length){
        Ques.innerHTML = QuestionList[index].question        
        }
        index += 1
        
    }
    
    
    // Ques.innerHTML = everythings.question
    
    // index += 1  
    // for (let option in everythings.answers)
    // {
    //     Ans.innerHTML = everythings.answers[option]
    //     option += 1
    // }

const Ques = document.getElementById("questions")
const Ans = document.getElementById("answers")
Ques.innerHTML = "Did it change anything?"
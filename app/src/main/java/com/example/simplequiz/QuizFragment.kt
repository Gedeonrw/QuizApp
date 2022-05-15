package com.example.simplequiz


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.simplequiz.databinding.FragmentQuizGameBinding
import kotlinx.android.synthetic.main.fragment_quiz_game.*

/**
 * A simple [Fragment] subclass.
 */
class QuizFragment : Fragment() {
   lateinit var binding: FragmentQuizGameBinding
    lateinit var currentQuestion:Question
    private var questionIndex = 0
    private val maxNumberOfQuestion = 3

    var questions:ArrayList<Question> = arrayListOf(
        Question("The two types of constructors in kotlin are : ",
            arrayListOf("Primary and secondary constructor","First and second constructor","Constant and parameterized constructor","None of these") ),
        Question("What hundles null exception in kotlin?",
            arrayListOf("Elvis operator","Sealed classes", "Lambda functions", "The kotlin extension")),
        Question("The correct function to get the length of the string in kotlin is",
            arrayListOf("str.length","String(length)","lengthof(str)","None of these")),
        Question("Under which license Kotlin is developed ",
            arrayListOf("2.0","1.1","1.6","2.5")),
        Question("What defined a sealed class in kotlin ",
            arrayListOf("It represented restricted class hierarchies","It is another name of the abstract class","It is used in every kotlin program","None of the these"))
    )

    lateinit var answers:ArrayList<String>
    lateinit var selectedAnswer:String
    var score:Int = 0
    var wrongAnswerList:ArrayList<String> = ArrayList()
    private fun setQuestion(){
        currentQuestion = questions[questionIndex]
        answers = ArrayList(currentQuestion.answerGroup)
        answers.shuffle()

        Log.d("ANSWERGROUP", answers[0]+ " "+answers[1]+ " "+answers[2]+ " "+answers[3]+ " ")
        Log.d("REALANSWER", currentQuestion.answerGroup[0])

    }

    private fun randomQuestion(){
        questions.shuffle()
        setQuestion()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_game,container,false)

        randomQuestion()
        binding.quiz=this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        option1.setOnClickListener{
            checkAnswer(option1.text.toString())
        }
        option2.setOnClickListener{
            checkAnswer(option2.text.toString())
        }
        option3.setOnClickListener{
            checkAnswer(option3.text.toString())
        }
        option4.setOnClickListener{
            checkAnswer(option4.text.toString())
        }

    }

    private fun checkAnswer(answer:String){
        if(answer.equals(currentQuestion.answerGroup[0])){
            score+=1
        }
        else{
            wrongAnswerList.add(currentQuestion.theQuestion)
        }
        questionIndex++
        if(questionIndex<=maxNumberOfQuestion){
            setQuestion()
            binding.invalidateAll()
        }
        else{
            getScore()
        }
    }

    private fun getScore(){
        if(score>=3){
        val action = QuizFragmentDirections.actionQuizFragment3ToQuizWonFragment(score ,wrongAnswerList.toTypedArray())
            view!!.findNavController().navigate(action)

        }
        else{
           val action = QuizFragmentDirections.actionQuizFragment3ToQuizLostFragment(score,wrongAnswerList.toTypedArray())
            view!!.findNavController().navigate(action)
        }
    }

}

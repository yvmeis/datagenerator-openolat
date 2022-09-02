<template>
  <div>
    <div class="titlewrapper" style="width: 50%; margin-left:auto; margin-right:auto; margin-top:3vh">
        <h3 class="sendText">Template Creation</h3>
        <div class="titleborder"></div>
    </div>
    <div v-if="pagecounter === 1" class="templatepage">
        <div class="randomcolumn1">
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px; visibility: hidden;" @click="counterDown()">Back</button>
            </div>
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="counterUp()">Next</button>
            </div>
        </div>
        <div class="randomcolumn1">
            <form class="classicbuttonform" style="display: flex; flex-direction: row; align-items: baseline;">
                <input class="settingsInput" type="text" id="tempnmbr" placeholder="Name"/><br>
                <input class="universalbutton" type="button" value="Create New Template" @click="createTemplate()"/>
            </form>
        </div>
    </div>
    <div v-if="pagecounter === 2" class="templatepage">
        <div class="randomcolumn1">
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="counterDown()">Back</button>
            </div>
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="counterUp()">Next</button>
            </div>
        </div>
        <div class="randomcolumn1">
            COURSES
        </div>
        <div class="randomcolumn1">
            <form class="classicbuttonform" style="display: flex; flex-direction: row; align-items: baseline;">
                <div class="tempcreatorfields">
                    <label for="coursenmbr">Name of Course*</label>
                    <input class="settingsInput" type="text" id="coursenmbr" placeholder="Name"/><br>
                </div>
                <div class="tempcreatorfields">
                    <label for="coursenmbr">Name of Template</label>
                    <input class="settingsInput" type="text" id="coursetemplatenumber" placeholder="Name"/><br>
                </div>
                <div class="tempcreatorfields">
                    <label for="coursenmbr">Number of Owners*</label>
                    <input class="settingsInput" type="text" id="ownersnumber" placeholder="Amount"/><br>
                </div>
                <div class="tempcreatorfields">
                    <label for="coursenmbr">Number of Tutors*</label>
                    <input class="settingsInput" type="text" id="tutorsnumber" placeholder="Amount"/><br>
                </div>
                <div class="tempcreatorfields">
                    <label >LectureBlocks</label>
                    <div>
                        <label for="bx1"> enabled </label>
                        <input class="sendCheckBox" type="checkbox" id="bx1"/><br>
                    </div>
                </div>
            </form>
        </div>
        <div class="randomcolumn1">
            <input class="universalbutton" type="button" value="Add Course" @click="addCourse()"/>
        </div>
            
    </div>
    <div v-if="pagecounter === 3" class="templatepage">
         <div class="randomcolumn1">
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="counterDown()">Back</button>
            </div>
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="counterUp()">Next</button>
            </div>
        </div>
    </div>
    <div v-if="pagecounter === 4" class="templatepage">
         <div class="randomcolumn1">
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="counterDown()">Back</button>
            </div>
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="counterUp()">Next</button>
            </div>
        </div>
    </div>
    <div v-if="pagecounter === 5" class="templatepage">
         <div class="randomcolumn1">
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="counterDown()">Back</button>
            </div>
            <div class="classicbuttonform">
                <button class="uploadbutton" style="font-size: 2vh; padding: 5px; visibility: hidden;" @click="counterUp()">Next</button>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import api from "../api/backend-api"

export default {

    name: 'TemplateCreator',

    data(){
        return{
            pagecounter: 1,
        }
    },

    methods: {  

        counterUp: function(){
            this.pagecounter +=1
        },

        counterDown: function(){
            this.pagecounter -=1
        },

        createTemplate: async function(){
            localStorage.setItem('templateName', document.getElementById('tempnmbr').value)
            await api.setUpNewTemplate(localStorage.getItem('templateName'))
        },

        addCourse: async function(){
            await api.putCourseInTemplate(localStorage.getItem('templateName')
                ,document.getElementById('coursenmbr').value
                ,document.getElementById('coursetemplatenumber').value
                ,document.getElementById('ownersnumber').value
                ,document.getElementById('tutorsnumber').value
                ,document.getElementById("bx1").checked)
        }
    }

}
</script>

<style>

</style>
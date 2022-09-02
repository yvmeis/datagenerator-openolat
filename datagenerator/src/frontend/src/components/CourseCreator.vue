<template>
    <div class = "inputPage">
        <LoadingBar v-if="loading"/>
        <div class="titlewrapper">
            <h3 class="sendText">1. Courses</h3>
            <div class="titleborder"></div>
        </div>
        <div class="contentwrapper">
            <div>
                <form class="classicbuttonform">
                    <input class="sendBar" type="number" id="coursenmbr" placeholder="AMOUNT"/><br>
                    <input class="sendButton" type="button" value="Generate" @click="create()"/>
                    <form>
                        <input class="sendButton" type="button" value="Add Owners" @click="addOwners()"/>
                    </form>
                    <form>
                        <input class="sendButton" type="button" value="Add Lecture" @click="addLectures()"/>
                    </form>
                </form>
            </div>
            <div class="tableWrapper">
                <h4>{{retrievedCourses.length}} current Courses:</h4>
                <div class="scrollable">  
                    <table class="usertable" >
                        <thead><tr><th></th><th>Key</th><th>Title</th></tr></thead>
                        <tbody id="coursetabledivbody"></tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import api from "../api/backend-api"
import LoadingBar from "../components/LoadingBar";


function delay(time) {
  return new Promise(resolve => setTimeout(resolve, time));
}

export default {

    name: 'CourseCreator',

    components: {
        LoadingBar
    },

    data(){
        return{
            reponse: [],
            retrievedCourses: {},
            data: [],
            loading: false,

        }
    },

    async mounted(){
        await api.getCourses().then(response =>{
            this.retrievedCourses = response.data;
        })
        this.showCourses();
    },

    methods:{

        create: async function(){
            if(document.getElementById("coursenmbr").value < 1){
                alert("Can't create less than 1 course");
            } else {
                this.loading = true;
                await api.createCourses(document.getElementById("coursenmbr").value)  
                await api.getCourses().then(response =>{
                    this.retrievedCourses = response.data;
                })
                this.loading = false;
                this.showCourses();
            }  
        },

        showCourses: async function(){
            
            this.data.length = 0;
            for(var i=0; i<this.retrievedCourses.length; i++){
                this.data.push(this.retrievedCourses[i].key);
                this.data.push(this.retrievedCourses[i].displayName);
            }
                await delay(1);
                this.createTable();
        },

        createTable: async function(){
            var mytable="";
            var count = 1;
            for (var cell of this.data) { 
                if (count === 1){
                    mytable += "<tr><td class='tdstarter'></td>";
                    mytable += "<td>" + cell + "</td>"; 
                    count = 2;
                } else if (count === 2){
                    mytable += "<td>" + cell + "</td></tr>"; 
                    count = 1;
                }
            }
            document.getElementById("coursetabledivbody").innerHTML = mytable;
        },

        addOwners: async function(){
            await api.addOwnersToCourses()
        },

        addLectures: async function(){
            await api.addLectures()
        }
    }

}
</script>

<style src="../assets/inputPages.css">

</style>
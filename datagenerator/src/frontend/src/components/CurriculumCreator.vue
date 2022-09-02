<template>
    <div class = "inputPage">
        <LoadingBar v-if="loading"/>
         <div class="titlewrapper">
            <h3 class="sendText">3. Curricula</h3>
            <div class="titleborder"></div>
        </div>
        <div class="contentwrapper">
            <div>
                <form class="classicbuttonform">
                    <input class="sendButton" type="button" value="GENERATE" @click="create()"/>
                </form>
            </div>
            <div class="tableWrapper">
                <h4>{{retrievedCurricula.length/2}} current Curricula:</h4>
                <div class="scrollable">  
                    <table class="usertable" >
                        <thead><tr><th></th><th>Key</th><th>Title</th></tr></thead>
                        <tbody id="curtabledivbody"></tbody>
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

    name: 'CurriculumCreator',

    components: {
        LoadingBar
    },


    data(){
        return{
            reponse: [],
            retrievedCurricula: {},
            loading: false,
        }
    },

    async mounted(){
        await api.getCurricula().then(response =>{
            this.retrievedCurricula = response.data;
        })
       
        this.showCurricula();
    },

    methods: {

        create: async function(){
            this.loading = true;
            await api.makeCurriculum();
            await api.getCurricula().then(response =>{
                this.retrievedCurricula = response.data;
            })
            this.loading = false;
            this.showCurricula();
        },

        showCurricula: async function(){


            await delay(1);

            var mytable="";
            var count = 1;
            for (var cell of this.retrievedCurricula) { 
                if (count === 1){
                    mytable += "<tr><td class='tdstarter'></td>";
                    mytable += "<td>" + cell + "</td>"; 
                    count = 2;
                } else if (count === 2){
                    mytable += "<td>" + cell + "</td></tr>"; 
                    count = 1;
                }
            }
            document.getElementById("curtabledivbody").innerHTML = mytable;
        },
    }
}
</script>

<style src="../assets/inputPages.css">

</style>
<template>
    <div class="inputPage">
        <LoadingBar v-if="loading"/>
        <div class="titlewrapper">
            <h3 class="sendText">4. Taxonomies</h3>
            <div class="titleborder"></div>
        </div>
        <div class="contentwrapper">
            <div>
                <form class="classicbuttonform">
                    <input class="sendBar" type="text" id="taxnmbr" placeholder="KEY"/><br>
                    <input class="sendButton" type="button" value="Fill up" @click="fill()"/>
                </form>
            </div>
            <div class="tableWrapper">
                <h4>{{(retrievedTaxonomies.length)/3}} current Taxonomies:</h4>
                <div class="scrollable">  
                    <table class="usertable" >
                        <thead><tr><th></th><th>Key</th><th>Identifier</th><th>Name</th></tr></thead>
                        <tbody id="taxtabledivbody"></tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import api from "../api/backend-api"
import LoadingBar from "../components/LoadingBar";


export default {

    name: 'TaxonomyComponent',

    components: {
        LoadingBar
    },

    data(){
        return{
            response:[],
            retrievedTaxonomies: {},
            loading: false,
        }
    },

    async mounted(){
        await api.getTaxonomies().then(response =>{
            this.retrievedTaxonomies = response.data;
        })
        this.createTable();
    },

    methods:{
        createTable: async function(){
            var mytable="";
            var count = 1;
            for (var cell of this.retrievedTaxonomies) { 
                if (count === 1){
                    mytable += "<tr><td class='tdstarter'></td>";
                    mytable += "<td>" + cell + "</td>"; 
                    count = 2;
                } else if (count === 2){
                    mytable += "<td>" + cell + "</td>"; 
                    count = 3;
                } else if (count === 3){
                    mytable += "<td>" + cell + "</td></tr>"; 
                    count = 1;
                }
            }
            document.getElementById("taxtabledivbody").innerHTML = mytable;
        },

        fill: async function(){
            if(document.getElementById("taxnmbr").value < 1){
                alert("Can't fill a non-existent Taxonomy");
            } else {
                this.loading = true;
                await api.fillTaxonomy(document.getElementById("taxnmbr").value);
                this.loading = false;
            }

        },
    }
}
</script>

<style src="../assets/inputPages.css">

</style>
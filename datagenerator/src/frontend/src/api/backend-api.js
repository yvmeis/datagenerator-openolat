import axios from 'axios'

const axiosApi = axios.create({
    baseURL: '/api',
    timeout: 1000000
});

export default{
    createUsers(number) {

        return axiosApi.put('/users/'+number, {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            });
    },

    getUsers(){
        return axiosApi.put('/users', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            });
    },

    createCourses(number) {
        return axiosApi.put('/courses/'+number, {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            });
    },

    getCourses(){
        return axiosApi.put('/courses', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            });
    },

    createGroups(number) {
        return axiosApi.put('/groups/'+number, {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            });
    },

    getGroups(){
        return axiosApi.put('/groups', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            });
    },

    setRoles(number, roles){
        return axiosApi.post('/roles/'+number+'/'+roles, {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    getUsersWithRoles(roles){
        return axiosApi.put('/role/'+roles, {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    deleteUsers(){
        return axiosApi.post('/users', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    deleteCourses(){
        return axiosApi.post('/courses', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    deleteGroups(){
        return axiosApi.post('/groups', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    cleanTaxonomies(){
        return axiosApi.post('/taxonomies', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    addOwnersToGroups(){
        return axiosApi.put('/groups/owners', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    addOwnersToCourses(){
        return axiosApi.put('/courses/owners', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    getTaxonomies(){
        return axiosApi.put('/taxonomy', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    fillTaxonomy(key){
        return axiosApi.post('/taxonomy/'+key, {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    getCurricula(){
        return axiosApi.post('/curriculum', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    makeCurriculum(){
        return axiosApi.put('/curriculum', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    addLectures(){
        return axiosApi.put('/lectures', {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    getFileStructure(){
        return axiosApi.get('/files')
    },

    runTemplate(name){
        return axiosApi.put('/custom/'+name, {
            url: localStorage.getItem('url'),
            username: localStorage.getItem('username'),
            password: localStorage.getItem('password')
            })
    },

    uploadTemplate(formData){
        return axiosApi.put('/files',
            formData,
            {
                headers:{
                    'Content-Type': 'multipart/form-data'
                }
            })
    },

    deleteTemplate(name){
        return axiosApi.delete('/files/'+name)
    },

    downloadTemplate(name){
        return axiosApi.get('/files/'+name)
    },

    setUpNewTemplate(name){
        return axiosApi.post('/files/'+name)
    },

    putCourseInTemplate(name,coursename,coursetemplatename,owners,tutors,lectureblocks){
        return axiosApi.post('/files/courses', {
            name: name,
            coursename: coursename,
            coursetemplatename: coursetemplatename,
            owners: owners,
            tutors: tutors,
            lectureblocks: lectureblocks
        })
    },

    login(password){
        return axiosApi.put('/login/'+password)
    }

    
}
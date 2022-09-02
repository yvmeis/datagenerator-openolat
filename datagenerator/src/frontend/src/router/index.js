import { createRouter, createWebHistory } from 'vue-router'
import LoginComponent from '../views/Login.vue'
import RandomComponent from '../views/Random.vue'
import CustomComponent from '../views/Custom.vue'
import CleaningComponent from '../views/Cleaning.vue'
import HelpComponent from '../views/Help.vue'
import TemplateCreator from '../views/TemplateCreator.vue'


const routes = [

    {path: '/login', component: LoginComponent},
    {path: '/random', component: RandomComponent},
    {path: '/premade', redirect: '/premade/creator'},
    {path: '/custom', component: CustomComponent},
    {path: '/templatecreation', component: TemplateCreator},
    {path: '/clean', component: CleaningComponent},
    {path: '/help', component: HelpComponent},
    {path: '/:pathMatch(.*)*', redirect:'/login'}

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

router.beforeEach(async (to) => {
    if (localStorage.getItem('password') === null && to.path !== '/login') {
      return { path: '/login' }
    }
  })

export default router;
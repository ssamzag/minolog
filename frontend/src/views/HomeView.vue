<script setup lang="ts">
import axios from "axios";
import {onMounted, ref} from "vue";

const posts = ref([]);

axios.get("/api/posts?page=1&size=5").then((response) => {
  response.data.forEach((r: any) => {
    posts.value.push(r);
  })
})
</script>

<template>
    <ul>
      <li v-for="post in posts" :key="post.id">
        <div>
          <router-link :to="{name: 'read', params: {postId: post.id}}">{{post.title}}</router-link>
          <h4>{{post.nickName}}</h4>
        </div>

        <div>
          {{ post.content }}
        </div>
      </li>
    </ul>
</template>

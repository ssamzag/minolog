<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from 'axios';
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true
  }
})

const post = ref({
  title: "",
  content: ""
});

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  });
});

const router = useRouter();

const edit = () => {
  axios.put(`/api/posts/${props.postId}`, post.value).then(() => {
    router.replace({name: "home"});
  });
}

</script>

<template>
  <div>
    <el-input v-model="post.title" input type="text" placeholder="제목을 입력해 주세요"/>
  </div>
  <div>
    <div>
      <el-input v-model="post.content" type="textarea" rows="15"/>
    </div>
    <el-button type="warning" @click="edit()">수정완료</el-button>
    <div></div>
  </div>

</template>

<style>

</style>

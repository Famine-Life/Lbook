<template>
    <div class="container-fluid py-5 home"  lazy="loaded">
        <div class="row justify-content-center py-5">
            <div class="text-center col-md-12">
                <div class="title">L-Book</div>
                <div class="subtitle mb-5">一个美观的线上书店</div>
                <div class="text">读书多了，容颜自然有改变，许多时候，自己可能以为许多看过的书籍都成了过眼云烟，不负记忆，其实他们仍是潜在的。在气质里，在谈吐上，在胸襟的无涯，当然也可能显露在生活和文字里。</div>
            </div>
            <div class="mt-4">
                <button class="btn btn-primary" @click="$router.push('/books')">开始探索</button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                imgUrl: '../../../public/images/home.png'
            }
        }
    }
</script>

<style scoped>
    .title{
        font-family: Calibri;
        font: 72px bold;
        color: #fff;
    }

    .subtitle{
        font-size: 16px;
        font-family: 等线;
        color: #fff;
    }

    .text{
        width: 60%;
        margin:0 auto;
        font-size: 20px;
        color: #fff;
    }

    .home[lazy=loaded]{
        height: 800px;
        background:url('../../../public/images/home.png');
        background-size:cover;
        background-position: center;
    }
</style>

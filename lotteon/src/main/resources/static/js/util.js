// fetch GET용
async function fetchGet(url){

    console.log("fetchData1...1");

    try{
        console.log("fetchData1...2");
        const response = await fetch(url);
        console.log("here1");

        if(!response.ok){
            console.log("here2");
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    }catch (err) {
        console.log(err)
    }
}

// fetch POST용
async function fetchPost(url, jsonData){

    console.log("fetchData2...1");

    try{
        console.log("fetchData2...2");
        const response = await fetch(url, {
            method: 'POST',
            headers: {"Content-type":"application/json"},
            body: JSON.stringify(jsonData)
        });
        console.log("fetchData2...3");

        if(!response.ok){
            console.log("fetchData2...4");
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("fetchData2...5 : " + data);

        return data;

    }catch (err) {
        console.log(err)
    }
}

// fetch DELETE용
async function fetchDelete(url){

    try{
        const response = await fetch(url, {
            method: 'DELETE'
        });

        if(!response.ok){
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    }catch (err) {
        console.log(err)
    }
}


// fetch PUT용
async function fetchPut(url, jsonData){

    try{
        const response = await fetch(url, {
            method: 'PUT',
            headers: {"Content-type":"application/json"},
            body: JSON.stringify(jsonData)
        });

        if(!response.ok){
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    }catch (err) {
        console.log(err)
    }
}

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
async function fetchPut(url, jsonData) {

    try {
        const response = await fetch(url, {
            method: 'PUT',
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(jsonData)
        });

        if (!response.ok) {
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    } catch (err) {
        console.log(err)
    }
}

    function validateNumber(input) {
        // 입력값에서 숫자 및 소수점을 제외한 문자 제거
        input.value = input.value.replace(/[^0-9.]/g, '');

        // 소수점이 두 번 이상 나타나지 않도록 제어
        if (input.value.split('.').length > 2) {
            input.value = input.value.replace(/\.+$/, '');
        }

        // 소수점으로 시작하거나 두 번째 소수점을 입력하는 경우 첫 번째 소수점만 유지
        if (input.value.startsWith('.')) {
            input.value = '0' + input.value;
        }
}

import * as React from "react";

export const LoginIcon = () => {
    return <img
        src="/images/Logo.svg"
        height="400px"
        width="400px"
        style={{
            margin: "-60px"
        }}
    />
}

export const HomeIcon = () => {
    return <img
        src="/images/Logo.svg"
        height="100%"
        width="100%"
    />
}

const CheeseImage = () => {
    return <img
        src="/images/cheese.png"
        height="350px"
        width="350px"
        style={{
            marginBottom: "-10px", marginTop: "-60px", marginLeft: "30px", marginRight: "-30px"
        }}
    />
}

const CheeseAngryImage = () => {
    return <img
        src="/images/cheese-angry.png"
        height="450px"
        width="450px"
        style={{
            marginBottom: "-90px", marginTop: "-80px"
        }}
    />
}

const CowDeadImage = () => {
    return <img
        src="/images/cow-dead.png"
        height="250px"
        width="250px"
        style={{
            marginBottom: "30px"
        }}
    />
}

const CowSadImage = () => {
    return <img
        src="/images/cow-sad.png"
        height="300px"
        width="300px"
        style={{
            marginTop: "-30px", marginBottom: "5px"
        }}
    />
}

export const ErrorImage = () => {
    const imageNumber = Math.floor(Math.random() * 4);
    const PickedImage = () => {
        switch (imageNumber) {
            case 0:
                return <CheeseImage/>
            case 1:
                return <CheeseAngryImage/>
            case 2:
                return <CowDeadImage/>
            default:
                return <CowSadImage/>
        }
    }

    return <PickedImage/>
}



const BoardHeader = () => {
    return (
        <div>
            <div className="h-28 bg-cover" style={{backgroundImage:'url("https://preview.redd.it/1kggrhhjubh31.jpg?width=2400&format=pjpg&auto=webp&s=e9783ef44a36f8855a8fad40b1ba15de947f53bb")'}} ></div>
            <div className="bg-reddit_dark-brighter">
            <div className="mx-6 relative flex">
          <div className="h-20 w-20 rounded-full overflow-hidden relative -top-3 border-4 border-white bg-white">
            <img src="https://b.thumbs.redditmedia.com/r-usRhC4xEa6Xh7scPFjQ-66CwcIfX7ga9psa3Vipkk.png" alt="" />
          </div>
          <div className="pt-2 pl-4">
          <h1 className="text-gray-300 text-3xl">Tomorrow: Iline for time travelers</h1>
          <h5 className="text-gray-500">i/tomorrow/</h5>
          </div>
        </div>
      </div>
        </div>
    )
}

export default BoardHeader

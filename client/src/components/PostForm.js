import Avatar from '../images/Avatar.png'

const PostForm = () => {
    return (
        <div>
            <div className="bg-reddit_dark px-6 py-4 text-gray-400">
        <div className="border border-reddit_border p-2 rounded-md flex">
          <div className="rounded-full bg-gray-600 overflow-hidden w-10 h-10">
            <img src={Avatar} alt="Profile" />
          </div>
          <form action="" className="flex-grow ml-4">
            <input type="text" className="bg-reddit-dark_brighter border border-reddit_border p-2 block w-full rounded-md " placeholder="New post" />
          </form>
        </div>

      </div>
        </div>
    )
}

export default PostForm

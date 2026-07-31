const products = [
  {
    id: 1,
    name: "All-Purpose Flour",
    category: "Flour",
    description: "All-Purpose flour is used for cookies, cakes, breads and more. ",
    price: 4.99,
    image: "https://media.istockphoto.com/id/498309202/photo/blank-flour-package-and-bowl.jpg?s=170667a&w=0&k=20&c=Jii_unfEzfN0E79_pyoP8l5US2uMNyHt11SuZROoDUY=",
  },
  {
    id: 2,
    name: "Bread Flour",
    category: "Flour",
    description: "High protein flour used for homemade breads and pizza dough.",
    price: 5.49,
    image: "https://tse2.mm.bing.net/th/id/OIP.yRPO-cwD91c4uxyeBkjn6wHaHa?r=0&rs=1&pid=ImgDetMain&o=7&rm=3",
  },
  {
    id: 3,
    name: "Cake Flour",
    category: "Flour",
    description: "Finely Milled Flour for fluffy cakes.",
    price: 4.79,
    image: "https://img.freepik.com/premium-photo/photo-wheat-flour-wheat-bars-isolated-background_1025753-52891.jpg",
  },
  {
    id: 4,
    name: "Whole Wheat Flour",
    category: "Flour",
    description: "Nutritious whole grain flour for breads and muffins.",
    price: 4.99,
    image: "https://media.istockphoto.com/id/1273286913/photo/wheat-flour-and-wheat-bars-on-a-white-background.jpg?s=612x612&w=0&k=20&c=-OkFunP6jslC972rsw_U5vwLU0LXz7aVYHEOYtb23lI=",
  },
  {
    id: 5,
    name: "Almond Flour",
    category: "Flour",
    description: "Gluten free flour made from finely ground almonds.",
    price: 8.49,
    image: "https://freshgrain.net/wp-content/uploads/2023/11/almond-flour-600x600.png",
  },
  {
    id: 6,
    name: "Granulated Sugar",
    category: "Sweetners",
    description: "Classic white sugar for baking and sweetening.",
    price: 3.79,
    image: "https://img.freepik.com/premium-photo/granulated-white-sugar-wooden-bowl-isolated-white-background-with-clipping-path_625448-268.jpg?w=2000",
  },
  {
    id: 7,
    name: "Brown Sugar",
    category: "Sweetners",
    description: "Soft brown sugar with a rich molasses flavor.",
    price: 2.99,
    image: "https://media.istockphoto.com/id/1288071155/photo/bowl-of-brown-sugar-isolated-on-white-background.jpg?s=170667a&w=0&k=20&c=OfGmLaUwD0wXTlz8snm9oSTFsaD0RJKbokDQvvNW_LU=",
  },
  {
    id: 8,
    name: "Powdered Sugar",
    category: "Sweetners",
    description: "Finely ground sugar ideal for frosting and icing.",
    price: 2.69,
    image: "https://thumbs.dreamstime.com/b/bowl-powder-sugar-isolated-white-background-top-view-bowl-powder-sugar-white-background-above-119025489.jpg",
  },

  {
  id: 9,
  name: "Honey",
  category: "Sweeteners",
  description: "Pure honey for baking, tea, or natural sweetening.",
  price: 6.49,
  image: "https://tse1.mm.bing.net/th/id/OIP.elM1DNhweU4Gq-lDawis5gHaFD?r=0&rs=1&pid=ImgDetMain&o=7&rm=3"
},
{
  id: 10,
  name: "Maple Syrup",
  category: "Sweeteners",
  description: "Rich maple syrup perfect for pancakes and baking.",
  price: 7.99,
  image: "https://media.istockphoto.com/id/1223457054/photo/bottle-of-maple-syrup-against-white-background.jpg?s=170667a&w=0&k=20&c=m3qLGNozvKjQUZ63afKEY5DmTgxKwJGrB-BCEhakZcs="
},
{
  id: 11,
  name: "Corn Syrup",
  category: "Sweeteners",
  description: "Light corn syrup used in candies and desserts.",
  price: 4.29,
  image: "https://img.freepik.com/premium-photo/isolated-corn-syrup-emphasizing-its-thick-sticky-cons-top-view-shot-white-background_655090-579373.jpg?w=996"
},
{
  id: 12,
  name: "Baking Powder",
  category: "Leavening",
  description: "Helps baked goods rise and become fluffy.",
  price: 2.49,
  image: "https://th.bing.com/th/id/OIP.49tO_YXraPhvhdGUVoRm-gHaE7?w=267&h=180&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3"
},
{
  id: 13,
  name: "Baking Soda",
  category: "Leavening",
  description: "Essential leavening ingredient for baking recipes.",
  price: 1.69,
  image: "https://static.vecteezy.com/system/resources/previews/026/975/424/large_2x/baking-soda-top-view-isolated-on-white-background-photo.jpg"
},
{
  id: 14,
  name: "Active Dry Yeast",
  category: "Leavening",
  description: "Dry yeast for homemade bread and rolls.",
  price: 3.49,
  image: "https://tse3.mm.bing.net/th/id/OIP._pQtWYHHauA-tkMZINIOLgHaHa?r=0&w=800&h=800&rs=1&pid=ImgDetMain&o=7&rm=3"
},
{
  id: 15,
  name: "Cocoa Powder",
  category: "Baking Essentials",
  description: "Unsweetened cocoa powder for chocolate desserts.",
  price: 4.29,
  image: "https://img.freepik.com/premium-photo/pile-cocoa-powder-white-background-white-clear-surface-png-transparent-background_94628-45680.jpg?w=2000"
},
{
  id: 16,
  name: "Cornstarch",
  category: "Baking Essentials",
  description: "Thickening agent for sauces, pies, and desserts.",
  price: 2.29,
  image: "https://static.vecteezy.com/system/resources/previews/049/332/413/large_2x/corn-cobs-lying-near-wooden-bowl-filled-with-flour-on-white-background-photo.jpg"
},
{
  id: 17,
  name: "Vanilla Extract",
  category: "Extracts & Flavoring",
  description: "Pure vanilla flavoring for baked goods and desserts.",
  price: 6.99,
  image: "https://static.vecteezy.com/system/resources/previews/059/651/936/non_2x/brown-glass-bottle-of-pure-vanilla-extract-with-vanilla-pods-png.png"
},
{
  id: 18,
  name: "Semi-Sweet Chocolate Chips",
  category: "Chocolate & Mix-ins",
  description: "Classic chocolate chips for cookies and brownies.",
  price: 3.49,
  image: "https://tse3.mm.bing.net/th/id/OIP.22vzf04495TfqA3Nwt8rWgHaFk?r=0&rs=1&pid=ImgDetMain&o=7&rm=3"
},
{
  id: 19,
  name: "Milk Chocolate Chips",
  category: "Chocolate & Mix-ins",
  description: "Creamy milk chocolate baking chips.",
  price: 3.49,
  image: "https://tse3.mm.bing.net/th/id/OIP.Yu_TareaLRJUxpK_dBgHaQHaEJ?r=0&rs=1&pid=ImgDetMain&o=7&rm=3"
},
{
  id: 20,
  name: "White Chocolate Chips",
  category: "Chocolate & Mix-ins",
  description: "Sweet white chocolate chips for desserts.",
  price: 3.69,
  image: "https://img.freepik.com/premium-photo/heap-white-chocolate-chips-close-up-white-background_857988-7260.jpg"
},
{
  id: 21,
  name: "Dark Chocolate Chunks",
  category: "Chocolate & Mix-ins",
  description: "Rich dark chocolate chunks for gourmet baking.",
  price: 4.49,
  image: "https://tse1.mm.bing.net/th/id/OIP.85Ge5Y2pGwbgTbnjfveE9gHaE8?r=0&rs=1&pid=ImgDetMain&o=7&rm=3"
},
{
  id: 22,
  name: "Butterscotch Chips",
  category: "Chocolate & Mix-ins",
  description: "Sweet butterscotch-flavored baking chips.",
  price: 3.29,
  image: "https://media.istockphoto.com/id/610116930/photo/butterscotch-baking-chips-isolated-on-white-background.jpg?s=170667a&w=0&k=20&c=5B2MJp-a7q96GnFO2OniYlWBU9Vzmq204xkh4slhI1g="
},
{
  id: 23,
  name: "Rainbow Sprinkles",
  category: "Decorations",
  description: "Colorful sprinkles for decorating desserts.",
  price: 2.99,
  image: "https://media.istockphoto.com/id/1205314691/photo/rainbow-sugar-sprinkles-on-white.jpg?s=612x612&w=0&k=20&c=GVRT2f04XM2h-AAmYWL1Wy44oy9VGQS5R5KLROUW3AY="
},
{
  id: 24,
  name: "Mini Marshmallows",
  category: "Toppings",
  description: "Soft mini marshmallows for treats and baking.",
  price: 2.49,
  image: "https://media.istockphoto.com/id/1296140276/photo/isolate-of-white-marshmallows-on-a-white-background-with-hard-shadows-copy-space.jpg?s=612x612&w=0&k=20&c=ujQjvztxY7FgRNdxIfw75y53dlAhGX5uAeiWWbvoQw8="
},
{
  id: 25,
  name: "Chopped Walnuts",
  category: "Nuts",
  description: "Fresh chopped walnuts for baking and toppings.",
  price: 5.99,
  image: "https://png.pngtree.com/png-clipart/20231119/original/pngtree-chopped-walnuts-closeup-background-photo-png-image_13643956.png"
},
{
  id: 26,
  name: "Pecans",
  category: "Nuts",
  description: "Premium pecan halves for pies and desserts.",
  price: 6.99,
  image: "https://tse4.mm.bing.net/th/id/OIP.A1a9jZe-uJu-jU37_Vcb8gHaHa?r=0&rs=1&pid=ImgDetMain&o=7&rm=3"
},
{
  id: 27,
  name: "Sliced Almonds",
  category: "Nuts",
  description: "Thinly sliced almonds for toppings and baking.",
  price: 4.99,
  image: "https://cdn11.bigcommerce.com/s-ap2s38wuhi/images/stencil/1280x1280/products/654/518/al05__74404.1641399069.jpg?c=2"
},
{
  id: 28,
  name: "Raisins",
  category: "Dried Fruit",
  description: "Naturally sweet dried grapes for baking recipes.",
  price: 3.29,
  image: "https://tse4.mm.bing.net/th/id/OIP.gnRlkY8VrvOI-XxITRTZAQHaGx?r=0&rs=1&pid=ImgDetMain&o=7&rm=3"
},
{
  id: 29,
  name: "Shredded Coconut",
  category: "Dried Fruit",
  description: "Sweetened shredded coconut for cakes and cookies.",
  price: 2.99,
  image: "https://thumbs.dreamstime.com/b/freshly-cut-shredded-coconut-isolated-white-background-highlighting-its-texture-tropical-nature-cut-shredded-366907597.jpg"
},
];

export default products;
Feature: Seller Search Query Service

Scenario: Search all sellers
When I POST a REST request to URL "/q/search-sellers" with payload
"""
{
	"filters" :{
		"deleted": false
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search sellers by business name with LIKE query
When I POST a REST request to URL "/q/search-sellers" with payload
"""
{
	"filters" :{
		"businessName": "Leather"
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search sellers by email
When I POST a REST request to URL "/q/search-sellers" with payload
"""
{
	"filters" :{
		"email": "seller1"
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search verified sellers
When I POST a REST request to URL "/q/search-sellers" with payload
"""
{
	"filters" :{
		"verified": true
	},
	"sortCriteria" :[
		{"name":"businessName","ascendingOrder": true}
	],
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search sellers by platform and state
When I POST a REST request to URL "/q/search-sellers" with payload
"""
{
	"filters" :{
		"platformId": "PLATFORM_001",
		"state": "ACTIVE"
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search suspended sellers
When I POST a REST request to URL "/q/search-sellers" with payload
"""
{
	"filters" :{
		"suspended": true
	},
	"sortCriteria" :[
		{"name":"createdAt","ascendingOrder": false}
	],
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search sellers with pagination
When I POST a REST request to URL "/q/search-sellers" with payload
"""
{
	"sortCriteria" :[
		{"name":"business_name","ascendingOrder": true}
	],
	"pageNum": 2,
	"numRowsInPage": 2
}
"""
Then the http status code is 200
And the top level code is 200
And success is true
And the REST response key "currentPage" is "2"
And the REST response key "numRowsInPage" is "2"


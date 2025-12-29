Feature: Platform Search Query Service

Scenario: Search all platforms
When I POST a REST request to URL "/q/search-platforms" with payload
"""
{
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search platforms by name with LIKE query
When I POST a REST request to URL "/q/search-platforms" with payload
"""
{
	"filters" :{
		"name": "India"
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search suspended platforms
When I POST a REST request to URL "/q/search-platforms" with payload
"""
{
	"filters" :{
		"suspended": true
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search platforms with sorting by name
When I POST a REST request to URL "/q/search-platforms" with payload
"""
{
	"sortCriteria" :[
		{"name":"name","ascendingOrder": true}
	],
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search platforms with pagination
When I POST a REST request to URL "/q/search-platforms" with payload
"""
{
	"pageNum": 1,
	"numRowsInPage": 5
}
"""
Then the http status code is 200
And the top level code is 200
And success is true
And the REST response key "numRowsInPage" is "5"
And the REST response key "currentPage" is "1"

Scenario: Get Seller Overview By ID
When I POST a REST request to URL "/q/platform-seller-overview" with payload
"""
{
	"filters": {
		"sellerId": "SEL-001"
	}
}
"""
Then the http status code is 200
And success is true

